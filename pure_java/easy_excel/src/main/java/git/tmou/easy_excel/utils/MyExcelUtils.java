package git.tmou.easy_excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import git.tmou.t_common.utils.ExcelUtils;
import git.tmou.t_common.utils.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyExcelUtils {
    public static final Logger log = LoggerFactory.getLogger(MyExcelUtils.class);
    private final static String logTemplate = "Method: %s, URL: %s, Params: %s";


    /**
     * @Description //下载文件通用方法
     * @Author Mou Tian
     * @Date 2023/9/30 17:05
     */
    public static File generateFile(String fileDirectory) throws IOException {
        // 生成excel保存
        File directory = new File(fileDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String excelFileName = LocalDateTime.now().toLocalDate() + "|" + LocalDateTime.now().toLocalTime() + "|" + System.currentTimeMillis();
        File tempFile = File.createTempFile(excelFileName, ".xlsx", directory);
        //返回文件的绝对路径
        return tempFile;
    }

    public static void writeData(int sheetNum, String sheetName, List<?> data, Class<?> clazz, File file) {
        ExcelWriter excelWriter = EasyExcel.write(file).registerWriteHandler(ExcelUtils.setFormCellStyle())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
        try {
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, sheetName).head(clazz).build();
            excelWriter.write(data, writeSheet);
        } catch (Exception e) {
            log.warn("download error" + e.getMessage());
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
//            response.getWriter().println(JSON.toJSONString(map));
//            return Response.failure(9999, JSON.toJSONString(map));
        } finally {
            assert excelWriter != null;
            excelWriter.finish();
        }
    }

    public static Resource download(String url, Map<String, Object> params) {
        RestTemplate restTemplate = RestClient.get().getRestTemplate();
        log.debug(String.format(logTemplate, "download & params", url, params.toString()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.add(entry.getKey(), entry.getValue().toString());
        }
        String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<Resource> response = restTemplate.exchange(
                newUrl, HttpMethod.GET, entity, Resource.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        return response.getBody();
    }
}

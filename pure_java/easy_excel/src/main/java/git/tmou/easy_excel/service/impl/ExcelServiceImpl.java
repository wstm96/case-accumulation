package git.tmou.easy_excel.service.impl;

import git.tmou.easy_excel.config.ExcelConfig;
import git.tmou.easy_excel.entity.KolCollectionReportVO;
import git.tmou.easy_excel.service.ExcelService;
import git.tmou.easy_excel.utils.MyExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;


/**
 * @Author Mou Tian
 * @Date 2023/9/30 16:35
 * @ClassName: ExcelServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    private static final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public ResponseEntity<Resource> downloadFile() {
        try {
            File tempFile = MyExcelUtils.generateFile(ExcelConfig.downloadFilePath);
            // 输出io数据流
            if (!tempFile.exists()) {

            }

            ArrayList<KolCollectionReportVO> data1 = new ArrayList<>();
            data1.add(new KolCollectionReportVO());
            MyExcelUtils.writeData(0, "sheet1", data1, KolCollectionReportVO.class, tempFile);
            MyExcelUtils.writeData(1, "sheet2", data1, KolCollectionReportVO.class, tempFile);

            // 读取Excel文件返回资源流
            InputStream inputStream = Files.newInputStream(tempFile.toPath());
            // 创建InputStreamResource对象
            InputStreamResource resource = new InputStreamResource(inputStream);
            // 返回ResponseEntity
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//               .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

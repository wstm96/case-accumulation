package git.tmou.easy_excel.controller;

import git.tmou.easy_excel.service.ExcelService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse response) throws IOException {
        InputStream fileInputStream =(InputStream) excelService.downloadFile().getBody();

        IOUtils.copy(Objects.requireNonNull(fileInputStream), response.getOutputStream());
        response.getOutputStream().flush();
        IOUtils.closeQuietly(fileInputStream);
    }

    @RequestMapping("/upload")
    public void uploadFile() {

    }
}

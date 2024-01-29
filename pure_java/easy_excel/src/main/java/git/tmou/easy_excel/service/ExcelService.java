package git.tmou.easy_excel.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 * @Author Mou Tian
 * @Date 2023/9/30 16:35
 * @ClassName: ExcelService
 * @Description: TODO
 * @Version 1.0
 */
public interface ExcelService {
    ResponseEntity<Resource> downloadFile();
}

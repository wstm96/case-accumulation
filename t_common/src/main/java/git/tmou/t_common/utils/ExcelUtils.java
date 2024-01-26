package git.tmou.t_common.utils;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * excel解析正则判断，对五个平台的链接进行正则判断
 */
public class ExcelUtils {

    private static final Log LOG = LogFactory.getLog(ExcelUtils.class);


    /**
     * 设置Excel样式
     *
     * @return com.alibaba.excel.write.style.HorizontalCellStyleStrategy
     * @title setCellStyle
     * @author y
     * @updateTime 2021/9/10 11:15
     */
    private static HorizontalCellStyleStrategy setCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE1.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }

    /**
     * 设置Excel样式
     *
     * @return com.alibaba.excel.write.style.HorizontalCellStyleStrategy
     * @title setFormCellStyle
     * @author y
     * @updateTime 2021/9/10 11:15
     */
    public static HorizontalCellStyleStrategy setFormCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("Calibri");
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteFont.setFontName("Calibri");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}

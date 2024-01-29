package git.tmou.easy_excel.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class KolCollectionReportVO {

    @ExcelProperty(value = "p_kolid2")
    private String pKolid;

    @ExcelProperty(value = "m_kolid")
    private String mKolid;

    @ExcelProperty(value = "kol_name")
    private String name;

    @ExcelProperty(value = "平台")
    private String platform;

    @ExcelProperty(value = "粉丝数")
    private Long fansNum;

    @ExcelProperty(value = "分类")
    private String kolCategory;

    @ExcelProperty(value = "级别")
    private String tier;

    private String timeRange;

    @ExcelProperty(value = "近4周触达UV")
    private Long activeViewerNum;

    @ExcelProperty(value = "周作品数量")
    private Double postCount;

    @ExcelProperty(value = "单条阅读UV")
    private Long postRead;

    @ExcelProperty(value = "单条互动")
    private Long postEngagement;

    @ExcelProperty(value = "ER")
    private String engagementRate;

    @ExcelProperty(value = "TA浓度%")
    private String taRatio;

    @ExcelProperty(value = "TA TGI")
    private Double taTgi;

    @ExcelProperty(value = "CPUV")
    private Double cpuv;

    @ExcelProperty(value = "CPE")
    private Double cpe;

    @ExcelProperty(value = "CPTA")
    private Double cpta;

    /**
     * 黑马指数
     */
    @ExcelProperty(value = "黑马指数")
    private Integer darkHorseIndex;

    @ExcelProperty(value = "合作次数")
    private Integer coopCount;

    @ExcelProperty(value = "seeding score")
    private Integer score;

    @ExcelProperty(value = {"一推", "价格"})
    private Long price1;

    @ExcelProperty(value = {"一推", "价格来源"})
    private String priceSource1;

    @ExcelProperty(value = {"一推", "单条阅读UV"})
    private Long postRead1;

    @ExcelProperty(value = {"一推", "CPUV"})
    private Double cpuv1;

    @ExcelProperty(value = {"一推", "单条互动"})
    private Long postEngagement1;

    @ExcelProperty(value = {"一推", "CPE"})
    private Double cpe1;

    @ExcelProperty(value = {"一推", "单条TA UV"})
    private Long taPostRead1;

    @ExcelProperty(value = {"一推", "CPTA"})
    private Double cpta1;

    @ExcelProperty(value = {"二推", "价格"})
    private Long price2;

    @ExcelProperty(value = {"二推", "价格来源"})
    private String priceSource2;

    @ExcelProperty(value = {"二推", "单条阅读UV"})
    private Long postRead2;

    @ExcelProperty(value = {"二推", "CPUV"})
    private Double cpuv2;

    @ExcelProperty(value = {"二推", "单条互动"})
    private Long postEngagement2;

    @ExcelProperty(value = {"二推", "CPE"})
    private Double cpe2;

    @ExcelProperty(value = {"二推", "单条TA UV"})
    private Long taPostRead2;

    @ExcelProperty(value = {"二推", "CPTA"})
    private Double cpta2;

    @ExcelProperty(value = "粉丝活跃度指数")
    private Double fansActiveIndex;

    @ExcelProperty(value = "作品水量指数")
    private Double waterIndex;

    @ExcelProperty(value = "作品高水量Alert")
    private String waterAlert;

    @ExcelProperty(value = "PR Risk")
    private String prRisk;

    @ExcelProperty(value = "内容质量指数")
    private Double contentQualityIndex;

    @ExcelProperty(value = "备注")
    private String memo;

    @ExcelProperty(value = "男")
    private Double gender0;

    @ExcelProperty(value = "女")
    private Double gender1;

    @ExcelProperty(value = "18-22岁")
    private Double age0;

    @ExcelProperty(value = "23-29岁")
    private Double age1;

    @ExcelProperty(value = "30-34岁")
    private Double age2;

    @ExcelProperty(value = "35-39岁")
    private Double age3;

    @ExcelProperty(value = "40-44岁")
    private Double age4;

    @ExcelProperty(value = "45-54岁")
    private Double age5;

    @ExcelProperty(value = ">=55岁")
    private Double age6;

    @ExcelProperty(value = "一线城市")
    private Double cityTier0;

    @ExcelProperty(value = "二线城市")
    private Double cityTier1;

    @ExcelProperty(value = "三线城市")
    private Double cityTier2;

    @ExcelProperty(value = "四线城市")
    private Double cityTier3;

    @ExcelProperty(value = "贫困")
    private Double income0;

    @ExcelProperty(value = "低收入")
    private Double income1;

    @ExcelProperty(value = "小康")
    private Double income2;

    @ExcelProperty(value = "中产")
    private Double income3;

    @ExcelProperty(value = "大众富裕")
    private Double income4;

    @ExcelProperty(value = "富裕")
    private Double income5;

    public String getpKolid() {
        return pKolid;
    }

    public void setpKolid(String pKolid) {
        this.pKolid = pKolid;
    }

    public String getmKolid() {
        return mKolid;
    }

    public void setmKolid(String mKolid) {
        this.mKolid = mKolid;
    }
}

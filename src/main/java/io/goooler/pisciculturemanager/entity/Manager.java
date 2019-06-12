package io.goooler.pisciculturemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 渔业养殖
 * </p>
 *
 * @author silicon
 * @since 2019-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Manager", description = "渔业养殖")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    @ApiModelProperty(value = "温度")
    private Double temperature;

    @ApiModelProperty(value = "氧含量")
    private Double oxygen;

    @ApiModelProperty(value = "酸碱度")
    private Double ph;

    @ApiModelProperty(value = "氨氮含量")
    private Double nitrogen;

    @ApiModelProperty(value = "亚硝酸盐含量")
    private Double nitrite;
}

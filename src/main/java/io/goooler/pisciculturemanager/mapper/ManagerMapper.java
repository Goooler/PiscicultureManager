package io.goooler.pisciculturemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.goooler.pisciculturemanager.entity.Manager;

import java.util.List;

/**
 * <p>
 * 渔业养殖 Mapper 接口
 * </p>
 *
 * @author silicon
 * @since 2019-05-18
 */
public interface ManagerMapper extends BaseMapper<Manager> {
    List<Manager> selectSome(Long num);

    List<Manager> selectSomeById(Long id);
}

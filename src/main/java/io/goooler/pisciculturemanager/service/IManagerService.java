package io.goooler.pisciculturemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.goooler.pisciculturemanager.entity.Manager;

import java.util.List;

/**
 * <p>
 * 渔业养殖 服务类
 * </p>
 *
 * @author silicon
 * @since 2019-05-18
 */
public interface IManagerService extends IService<Manager> {
    List<Manager> selectSome(Long number);

    List<Manager> selectSomeById(Long id);
}

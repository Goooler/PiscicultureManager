package io.goooler.pisciculturemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.goooler.pisciculturemanager.entity.Manager;
import io.goooler.pisciculturemanager.mapper.ManagerMapper;
import io.goooler.pisciculturemanager.service.IManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 渔业养殖 服务实现类
 * </p>
 *
 * @author silicon
 * @since 2019-05-18
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Resource
    ManagerMapper managerMapper;

    @Override
    public List<Manager> selectSome(Long number) {
        return managerMapper.selectSome(number);
    }

    @Override
    public List<Manager> selectSomeById(Long id) {
        return managerMapper.selectSomeById(id);
    }
}

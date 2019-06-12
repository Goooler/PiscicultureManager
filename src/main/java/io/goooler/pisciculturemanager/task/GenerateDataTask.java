package io.goooler.pisciculturemanager.task;

import io.goooler.pisciculturemanager.entity.Manager;
import io.goooler.pisciculturemanager.service.IManagerService;
import io.goooler.pisciculturemanager.utils.GenerateDataUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class GenerateDataTask {
    @Resource
    IManagerService iManagerService;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void test1() {
        Manager manager = GenerateDataUtil.generate();
        iManagerService.save(manager);
    }
}

package io.goooler.pisciculturemanager.controller;


import io.goooler.pisciculturemanager.entity.Manager;
import io.goooler.pisciculturemanager.modules.R;
import io.goooler.pisciculturemanager.service.IManagerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 渔业养殖 前端控制器
 * </p>
 *
 * @author silicon
 * @since 2019-05-18
 */
@RequestMapping("/piscicultureManager")
@RestController
public class ManagerController {
    @Resource
    IManagerService managerService;

    @GetMapping("/allData/{number}")
    public R getAllData(@PathVariable("number") Long number) {
        List<Manager> managers = managerService.selectSome(number);
        return R.ok().put("data", managers);
    }

    @PostMapping("/add")
    public R addManager(@RequestBody Manager manager) {
        managerService.save(manager);
        return R.ok();
    }

    @GetMapping("/data/{id}")
    public R getData(@PathVariable("id") Long id) {
        Manager manager = managerService.getById(id);
        return R.ok().put("data", manager);
    }

    @GetMapping("/some/{id}")
    public R getSomeData(@PathVariable("id") Long id) {
        List<Manager> managers = managerService.selectSomeById(id);
        return R.ok().put("data", managers);
    }
}

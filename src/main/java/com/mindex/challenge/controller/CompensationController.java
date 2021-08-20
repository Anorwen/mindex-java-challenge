package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        if(compensation != null) {
            if(compensation.isValid()) {
                LOG.debug("Received compensation create request for [{}]", compensation.getEmployeeId());

                return compensationService.create(compensation);
            }
            LOG.debug("WARNING: Received invalid compensation object");
        }
        LOG.debug("WARNING: Did not receive a compensation object");
        return null;
    }

    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for [{}]", id);

        return compensationService.read(id);
    }
}

package com.example.eksamensprojekt.controllers;

import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.services.ModuleService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }








}

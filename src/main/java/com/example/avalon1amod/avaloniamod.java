package com.example.avalon1amod;

import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class avaloniamod extends MeteorAddon {

    @Override
    public void onInitialize() {
        System.out.println("avalon1amod loaded!");

        Modules.get().add(new HighwayNOOBFix());
        Modules.get().add(new HighwayYESOBFix());
    }

    @Override
    public void onRegisterCategories() {
    }

    @Override
    public String getPackage() {
        return "com.example.avalon1amod";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("avalon1an", "avalonia-mod");
    }

    @Override
    public String getCommit() {
        return "";
    }
}

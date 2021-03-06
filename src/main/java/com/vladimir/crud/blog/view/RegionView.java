package com.vladimir.crud.blog.view;

import com.vladimir.crud.blog.controller.RegionController;
import com.vladimir.crud.blog.model.Region;
import com.vladimir.crud.blog.service.hibernate.ServiceException;

import java.util.List;
import java.util.Scanner;

public class RegionView implements View{
    private final Scanner SCANNER = new Scanner(System.in);
    RegionController regionController;

    public RegionView() {
        this.regionController = new RegionController();
    }

    @Override
    public void create(){
        System.out.print("Enter name of the new region:");
        System.out.println("Added new region "
                + regionController.addRegion(SCANNER.nextLine().trim()).toString());
    }

    @Override
    public void readAll(){
        List<Region> regions = regionController.getAll();

        if(regions.size() == 0){
            System.out.println("No regions in repository");
            return;
        }
        System.out.println("List of all regions:");
        regions.stream().map(Region::toString)
                .forEachOrdered(System.out::println);
    }

    @Override
    public void read(Long id){
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo((long)0) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }
        Region region = null;
        try {
            region = regionController.getByID(id);
            System.out.println(region);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void update(Long id) {
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo((long)0) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }
        System.out.print("Type new region name:");
        Region region = new Region(id, SCANNER.nextLine().trim());
        try {
            regionController.update(region);
        } catch (ServiceException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Region with ID " + id + " has been changed successfully");

    }

    @Override
    public void delete(Long id) {
        if(id == null) throw new IllegalArgumentException();
        if(id.compareTo(0L) < 1){
            System.out.println("ID always should be greater than 0");
            return;
        }

        try{
            regionController.deleteByID(id);
        } catch (ServiceException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Region with id " + id + " has been deleted successfully");
    }
}

package com.iee.mongodb.opc.utgard.test;

import org.openscada.opc.dcom.list.ClassDetails;
import org.openscada.opc.lib.list.Categories;
import org.openscada.opc.lib.list.Category;
import org.openscada.opc.lib.list.ServerList;

import java.util.Collection;
import java.util.Locale;

/**
 * @ClassName GetOpcLink
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/7 0007 11:17
 */
public class GetOpcLink {
    public static void main(String[] args) throws Exception{
        ServerList serverList = new ServerList("10.1.5.123", "freud",
                "password", "");

        Collection<ClassDetails> classDetails = serverList
                .listServersWithDetails(new Category[] {
                        Categories.OPCDAServer10, Categories.OPCDAServer20,
                        Categories.OPCDAServer30 }, new Category[] {});

        for (ClassDetails cds : classDetails) {
            System.out.println(cds.getProgId() + "=" + cds.getDescription());
        }
    }
}

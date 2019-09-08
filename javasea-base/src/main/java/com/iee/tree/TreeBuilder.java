package com.iee.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeBuilder {

    @SuppressWarnings("unchecked")
    private List<Node> buildListToTree(List<Node> dirs) {
        List<Node> roots = findRoots(dirs);
        List<Node> notRoots = (List<Node>) CollectionUtils
                .subtract(dirs, roots);
        for (Node root : roots) {
            root.setChildren(findChildren(root, notRoots));
        }
        return roots;
    }

    public List<Node> findRoots(List<Node> allNodes) {
        List<Node> results = new ArrayList<Node>();
        for (Node node : allNodes) {
            boolean isRoot = true;
            for (Node comparedOne : allNodes) {
                if (node.getParentId() == comparedOne.getId()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                node.setLevel(0);
                results.add(node);
                node.setRootId(node.getId());
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private List<Node> findChildren(Node root, List<Node> allNodes) {
        List<Node> children = new ArrayList<Node>();

        for (Node comparedOne : allNodes) {
            if (comparedOne.getParentId() == root.getId()) {
                comparedOne.setParent(root);
                comparedOne.setLevel(root.getLevel() + 1);
                children.add(comparedOne);
            }
        }
        List<Node> notChildren = (List<Node>) CollectionUtils.subtract(allNodes, children);
        for (Node child : children) {
            List<Node> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                child.setLeaf(true);
            } else {
                child.setLeaf(false);
            }
            child.setChildren(tmpChildren);
        }
        return children;
    }

    public static void main(String[] args) {
        TreeBuilder tb = new TreeBuilder();
        List<Node> allNodes = new ArrayList<Node>();
        allNodes.add(new Node(1, 0, "节点1"));
        allNodes.add(new Node(2, 0, "节点2"));
        allNodes.add(new Node(3, 0, "节点3"));
        allNodes.add(new Node(4, 1, "节点4"));
        allNodes.add(new Node(5, 1, "节点5"));
        allNodes.add(new Node(6, 1, "节点6"));
        allNodes.add(new Node(7, 4, "节点7"));
        allNodes.add(new Node(8, 4, "节点8"));
        allNodes.add(new Node(9, 5, "节点9"));
        allNodes.add(new Node(10, 100, "节点10"));
        List<Node> roots = tb.buildListToTree(allNodes);
        System.out.println(roots);
        Set s = new HashSet<>();
//        getChildrenIds(roots, s);
        List<Node> findChildren = tb.findChildren(new Node(1, 0, "节点1") , allNodes);
        getChildrenIds(findChildren, s);
        System.out.println(s);
    }

    /** 递归查找子节点的ID */
	private static void getChildrenIds(List<Node> roots,Set s) {
		if(roots == null){
			return;
		}
		for (Node node : roots) {
			List<Node> childrens = node.getChildren();
			s.add(node.getId());
			getChildrenIds(childrens,s);
		}
	}
}

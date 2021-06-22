package com.shujia.common.kylin.cube;

import java.util.ArrayList;
import java.util.List;

class SelectRule{
    private List<String> hierarchy_dims;
    private List<String> mandatory_dims;
    private ArrayList<ArrayList<String>> joint_dims;

    public List<String> getHierarchy_dims() {
        return hierarchy_dims;
    }

    public void setHierarchy_dims(List<String> hierarchy_dims) {
        this.hierarchy_dims = hierarchy_dims;
    }

    public List<String> getMandatory_dims() {
        return mandatory_dims;
    }

    public void setMandatory_dims(List<String> mandatory_dims) {
        this.mandatory_dims = mandatory_dims;
    }

    public ArrayList<ArrayList<String>> getJoint_dims() {
        return joint_dims;
    }

    public void setJoint_dims(ArrayList<ArrayList<String>> joint_dims) {
        this.joint_dims = joint_dims;
    }
}
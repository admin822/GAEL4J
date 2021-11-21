package com.gael4j.Entity;

public class ChildNode {
	private boolean isBidirectional=false;
	private Class<?> nodeClass;
	private String targetFieldName;
	public boolean isBidirectional() {
		return isBidirectional;
	}
	public void setBidirectional(boolean isBidirectional) {
		this.isBidirectional = isBidirectional;
	}
	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}
	public String getTargetFieldName() {
		return targetFieldName;
	}
	public Class<?> getNodeClass() {
		return nodeClass;
	}
	public void setNodeClass(Class<?> nodeClass) {
		this.nodeClass = nodeClass;
	}
	@Override
	public String toString() {
		return "Node:[Class=" + nodeClass.getSimpleName() +
				", isBidirectional="+  isBidirectional +
				", targetField=" + targetFieldName + "]";
	}
	
}

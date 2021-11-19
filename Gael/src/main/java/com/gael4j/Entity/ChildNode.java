package com.gael4j.Entity;

public class ChildNode {
	private boolean isBidirectional=false;
	private Class<?> nodeClass;
	public boolean isBidirectional() {
		return isBidirectional;
	}
	public void setBidirectional(boolean isBidirectional) {
		this.isBidirectional = isBidirectional;
	}
	public Class<?> getNodeClass() {
		return nodeClass;
	}
	public void setNodeClass(Class<?> nodeClass) {
		this.nodeClass = nodeClass;
	}
	
}

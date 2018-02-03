package model;

import physics.Vector;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class CollisionDetails {
	private double tuc;
	private Vector velo;

	public CollisionDetails(double t, Vector v) {
		tuc = t;
		velo = v;
	}

	public double getTuc() {
		return tuc;
	}
	
	public Vector getVelo() {
		return velo;
	}

}

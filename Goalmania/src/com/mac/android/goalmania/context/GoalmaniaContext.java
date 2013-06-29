package com.mac.android.goalmania.context;

import com.mac.android.goalmania.model.Order;

public class GoalmaniaContext extends BaseApplication {

	protected Order order;
	
	public GoalmaniaContext() {
		super();
		
		this.order = new Order();
	}

	public Order getOrder() {
		return order;
	}
}

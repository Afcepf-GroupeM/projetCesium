package fr.afcepf.al29.groupem.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.afcepf.al29.groupem.business.api.ItemBusApi;
import fr.afcepf.al29.groupem.business.api.OrderBusApi;
import fr.afcepf.al29.groupem.entities.Item;
import fr.afcepf.al29.groupem.entities.Order;
import fr.afcepf.al29.groupem.entities.OrderState;

@Component
@ManagedBean
public class AdministrationController {
	
	
	private double caToday;
	private double caMonth;
	private double caTotal;
	private int ordersToday;
	private int ordersMonth;
	private int ordersTotal;
	
	private List<Order> ordersToPrepare; // First 5 found
	private List<Item> itemsLowStock; // First 5 found
	
	@Autowired
	private OrderBusApi orderBus;
	
	@Autowired
	private ItemBusApi itemBus;
	
	
	
	public void initAdmin(ComponentSystemEvent c){
		
		// Get the first 5 items with stock below 6
		itemsLowStock = new ArrayList<>();
		List<Item> itemsStockLessThanSix = itemBus.getItemsByStockLessthan(6);
		int iItem = 0;
		Iterator<Item> itItem = itemsStockLessThanSix.iterator();
		while (itItem.hasNext() && iItem < 5) {
			itemsLowStock.add(itItem.next());
			iItem++;
		}
		
		// Get the first 5 orders with state "EnPreparation"
		ordersToPrepare = new ArrayList<>();
		List<Order> ordersToPrepareAll = orderBus.getOrdersByState(OrderState.EnPreparation);
		int iOrder = 0;
		Iterator<Order> itOrder = ordersToPrepareAll.iterator();
		while (itOrder.hasNext() && iOrder < 5) {
			ordersToPrepare.add(itOrder.next());
			iOrder++;
		}
		
		// Get the numbrer of orders and total amount since 24h
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -1);
		ordersToday = orderBus.getNumberOfOrdersSince(date.getTime());
		caToday = orderBus.getTotalPriceForAll(orderBus.getOrdersSince(date.getTime()));
		
		
		// Get the numbrer of orders and total amount since last month
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, -1);
		ordersMonth = orderBus.getNumberOfOrdersSince(date2.getTime());
		caMonth = orderBus.getTotalPriceForAll(orderBus.getOrdersSince(date2.getTime()));
			
		
		// Get the numbrer of orders and total amount since 24h
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.YEAR, -25);
		ordersTotal = orderBus.getNumberOfOrdersSince(date3.getTime());
		caTotal = orderBus.getTotalPriceForAll(orderBus.getOrdersSince(date3.getTime()));
		
		
	}



	public double getCaToday() {
		return caToday;
	}



	public void setCaToday(double caToday) {
		this.caToday = caToday;
	}



	public double getCaMonth() {
		return caMonth;
	}



	public void setCaMonth(double caMonth) {
		this.caMonth = caMonth;
	}



	public double getCaTotal() {
		return caTotal;
	}



	public void setCaTotal(double caTotal) {
		this.caTotal = caTotal;
	}



	public int getOrdersToday() {
		return ordersToday;
	}



	public void setOrdersToday(int ordersToday) {
		this.ordersToday = ordersToday;
	}



	public int getOrdersMonth() {
		return ordersMonth;
	}



	public void setOrdersMonth(int ordersMonth) {
		this.ordersMonth = ordersMonth;
	}



	public int getOrdersTotal() {
		return ordersTotal;
	}



	public void setOrdersTotal(int ordersTotal) {
		this.ordersTotal = ordersTotal;
	}



	public List<Order> getOrdersToPrepare() {
		return ordersToPrepare;
	}



	public void setOrdersToPrepare(List<Order> ordersToPrepare) {
		this.ordersToPrepare = ordersToPrepare;
	}



	public List<Item> getItemsLowStock() {
		return itemsLowStock;
	}



	public void setItemsLowStock(List<Item> itemsLowStock) {
		this.itemsLowStock = itemsLowStock;
	}



	public OrderBusApi getOrderBus() {
		return orderBus;
	}



	public void setOrderBus(OrderBusApi orderBus) {
		this.orderBus = orderBus;
	}



	public ItemBusApi getItemBus() {
		return itemBus;
	}



	public void setItemBus(ItemBusApi itemBus) {
		this.itemBus = itemBus;
	}

	
	
}
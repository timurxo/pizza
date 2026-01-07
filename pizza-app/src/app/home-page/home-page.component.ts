import { Component, OnInit, ChangeDetectorRef } from '@angular/core';

import { PizzaOrder } from '../models/PizzaOrder';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
  standalone: false
})
export class HomePage implements OnInit {
  orders: PizzaOrder[] = [];

  constructor(
    private orderService: OrderService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.fetchOrders();
  }

  async fetchOrders() {
    this.orders = await this.orderService.getOrders();
    console.log('Fetched orders:', this.orders);
    this.cd.detectChanges();
  }

  onOrderPlaced(order: PizzaOrder) {
    this.orders.push(order);
    this.cd.detectChanges();
    // this.fetchOrders();
  }

  async onCancelOrder(id: number) {
    await this.orderService.cancelOrder(id);
    const orderIndex = this.orders.findIndex(o => o.id === id);
    this.orders[orderIndex].status = 'CANCELLED';
    this.cd.detectChanges();
  }

  async onDeleteOrder(id: number) {
    await this.orderService.deleteOrder(id);
    this.orders = this.orders.filter(o => o.id !== id);
    this.cd.detectChanges();
  }
}

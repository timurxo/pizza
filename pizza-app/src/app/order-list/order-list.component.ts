import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { OrderService } from '../services/order.service';
import { PizzaOrder } from '../models/PizzaOrder';


@Component({
    selector: 'app-order-list',
    templateUrl: './order-list.component.html',
    styleUrl: './order-list.component.css',
    standalone: false
})
export class OrderListComponent implements OnChanges {
    @Input() orders: PizzaOrder[] = [];
    @Output() cancel = new EventEmitter<number>();
    @Output() delete = new EventEmitter<number>();

    searchControl = new FormControl('');
    filteredOrders: PizzaOrder[] = [];

    constructor() {

    }

    ngOnInit() {
        this.searchControl.valueChanges.subscribe(val => {
            this.filterOrders(val?.toLowerCase() || '');
        });
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['orders']) {
            this.filterOrders(this.searchControl.value?.toLowerCase() || '');
        }
    }

    filterOrders(word: string) {
        if (!word) {
            this.filteredOrders = this.orders;
            return;
        }

        this.filteredOrders = this.orders.filter(order =>
            order.tableNo.toString().includes(word) ||
            order.crust.toLowerCase().includes(word) ||
            order.topping.toLowerCase().includes(word) ||
            order.size.toLowerCase().includes(word) ||
            order.status?.toLocaleLowerCase().includes(word)
        );
    }

    async cancelOrder(id: number) {
        if (confirm('are you sure you want to cancel this order?')) {
            this.cancel.emit(id);
        }
    }

    async deleteOrder(id: number) {
        if (confirm('permanently delete order?')) {
            this.delete.emit(id);
        }
    }
}

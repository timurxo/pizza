import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { PizzaOrder } from '../models/PizzaOrder';

@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) { }

    async getOrders(): Promise<PizzaOrder[]> {
        const url = this.apiUrl + "/getOrders";
        // let params = {};
        return firstValueFrom(this.http.get<PizzaOrder[]>(url)); // , { params }
    }

    async addOrder(order: PizzaOrder): Promise<PizzaOrder> {
        const url = this.apiUrl + "/createOrder";
        return firstValueFrom(this.http.post<PizzaOrder>(url, order));
    }

    async cancelOrder(id: number): Promise<void> {
        const url = this.apiUrl + "/cancelOrder";
        return firstValueFrom(this.http.patch<void>(url, {}, { params: { id } }));
    }

    async deleteOrder(id: number): Promise<void> {
        const url = this.apiUrl + "/deleteOrder";
        return firstValueFrom(this.http.delete<void>(url, { params: { id } }));
    }
}

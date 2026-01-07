import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from '../services/order.service';
import { PizzaOrder } from '../models/PizzaOrder';

@Component({
    selector: 'app-order-form',
    templateUrl: './order-form.component.html',
    styleUrl: './order-form.component.css',
    standalone: false
})
export class OrderFormComponent implements OnInit {
    @Output() orderPlaced = new EventEmitter<PizzaOrder>();

    successMessage: string = '';
    errorMessage: string = '';
    orderForm: FormGroup;

    crusts = ['thin', 'thick', 'stuffed'];
    toppings = ['pepperoni', 'mushrooms', 'onions'];
    sizes = ['small', 'medium', 'large'];

    constructor(
        private fb: FormBuilder,
        private orderService: OrderService,
        private cd: ChangeDetectorRef
    ) {
        this.orderForm = this.fb.group({
            crust: ['', Validators.required],
            topping: ['', Validators.required],
            size: ['', Validators.required],
            tableNo: ['', [Validators.required, Validators.min(1)]]
        });
    }

    ngOnInit(): void {

    }

    createRequestPayload(): PizzaOrder {
        const payload: PizzaOrder = {
            crust: this.orderForm.get('crust')?.value,
            topping: this.orderForm.get('topping')?.value,
            size: this.orderForm.get('size')?.value,
            tableNo: Number(this.orderForm.get('tableNo')?.value),
            status: 'PLACED'
        };

        return payload;
    }

    async onSubmit() {
        if (!this.orderForm.valid) {
            return;
        }

        try {
            const payload = this.createRequestPayload();
            // console.log('current payload: ', payload);

            const newOrder = await this.orderService.addOrder(payload);

            this.successMessage = 'Order placed successfully!';
            this.orderPlaced.emit(newOrder);

            this.errorMessage = '';
            this.orderForm.reset();

            setTimeout(() => {
                this.successMessage = '';
                this.cd.detectChanges();
            }, 3000);
        } catch (error: any) {

            // console.log('current error: ', error);

            this.errorMessage = error?.error?.message ? error.error.message : 'Failed to place order';
            this.successMessage = '';
            this.cd.detectChanges();
        }
    }
}


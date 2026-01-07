import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OrderFormComponent } from './order-form.component';
import { AppModule } from '../app.module';
import { OrderService } from '../services/order.service';

describe('OrderFormComponent', () => {
    let component: OrderFormComponent;
    let fixture: ComponentFixture<OrderFormComponent>;
    let mockOrderService = {
        addOrder: (order: any) => Promise.resolve({} as any)
    };

    beforeEach(async () => {
        mockOrderService.addOrder = (order: any) => Promise.resolve({} as any);

        await TestBed.configureTestingModule({
            imports: [AppModule],
            providers: [
                { provide: OrderService, useValue: mockOrderService }
            ]
        })
            .compileComponents();

        fixture = TestBed.createComponent(OrderFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

});

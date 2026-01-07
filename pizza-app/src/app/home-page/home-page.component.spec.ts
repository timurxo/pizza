import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomePage } from './home-page.component';
import { OrderService } from '../services/order.service';
import { AppModule } from '../app.module';

describe('HomePage', () => {
  let component: HomePage;
  let fixture: ComponentFixture<HomePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
      providers: [
        {
          provide: OrderService,
          useValue: {
            getOrders: () => [],
            addOrder: () => Promise.resolve(),
            cancelOrder: () => Promise.resolve()
          }
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

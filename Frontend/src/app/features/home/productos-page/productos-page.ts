import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar-component/navbar-component';
import { FooterComponent } from '../footer-component/footer-component';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [NavbarComponent, FooterComponent],
  templateUrl: './productos-page.html',
})
export class ProductosPage { }

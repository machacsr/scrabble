import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CubeComponent } from './cube/cube.component';
import { TableComponent } from './table/table.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    CubeComponent,
    TableComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    CubeComponent,
    TableComponent,
    HeaderComponent
  ]
})
export class ComponentsModule { }

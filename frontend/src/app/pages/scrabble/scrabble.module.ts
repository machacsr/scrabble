import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScrabbleComponent } from './scrabble.component';
import { ComponentsModule } from 'src/app/components/components.module';
import { ScrabbleRoutingModule } from './scrabble-routing.module';


@NgModule({
  declarations: [
    ScrabbleComponent
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    ScrabbleRoutingModule,
  ]
})
export class ScrabbleModule { }

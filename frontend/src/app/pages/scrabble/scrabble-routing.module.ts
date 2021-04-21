import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ScrabbleComponent } from './scrabble.component';


const routes: Routes = [
    {
      path: '',
      component: ScrabbleComponent,
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScrabbleRoutingModule { }

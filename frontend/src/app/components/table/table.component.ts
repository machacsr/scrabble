import { Component, OnInit, Output } from '@angular/core';
import { DataService } from 'src/app/services/data.service';

export class Cell {
  value: string;
  locked = false;
}

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

  table: Cell[][];
  console = console;

  bottomRow = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'];

  constructor(
    private dataService: DataService
  ) { 

  }

  ngOnInit(): void {
    this.init();
  }

  processCharacter(cell: Cell, rowIndex: number, columnIndex: string) {
    if (!cell.value) {
      return;
    }
    this.dataService.scrabble
        .move({move: {rowIndex, columnIndex}, character: cell.value.toUpperCase()})
        .subscribe(
          () => {
            cell.locked = true;
          },
          (error: any) => {
            alert(error.error.message);
          }
        );
  }


  init() {
    this.table = [];
    for (let i: number = 0; i < 15; i++) {
      this.table[i] = [];
      for (let j: number = 0; j < 15; j++) {
          this.table[i][j] = new Cell();
      }
    }
  }

}

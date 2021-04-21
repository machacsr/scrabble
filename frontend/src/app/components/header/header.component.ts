import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DataService, Word } from 'src/app/services/data.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() recommendedWords: Word[] = [];
  @Output() newRecommendedWordButtonClicked = new EventEmitter();
  @Output() newGameButtonClicked = new EventEmitter();

  textAreaModel: string;

  constructor(
    private dataService: DataService
  ) { }

  ngOnInit(): void {
  }


  newRecommendedWordButton() {

    if (!this.textAreaModel) {
      alert("Üres a text area!");
    }

    this.newRecommendedWordButtonClicked.emit(this.textAreaModel); 
  }

  getNewTiles() {
    this.dataService.scrabble.pull().subscribe(
      (response) => {
        this.textAreaModel = response.tiles.join(',');
      });
  }

  newGame() {
    this.dataService.scrabble.newGame().subscribe(
      (response) => {
        this.textAreaModel = '';
        this.newGameButtonClicked.emit();
        alert('Új játék!');
      });
  }
}

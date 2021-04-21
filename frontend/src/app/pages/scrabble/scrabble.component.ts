import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from 'src/app/components/table/table.component';
import { DataService, Word } from 'src/app/services/data.service';

@Component({
  selector: 'app-scrabble',
  templateUrl: './scrabble.component.html',
  styleUrls: ['./scrabble.component.scss']
})
export class ScrabbleComponent implements OnInit {

  @ViewChild('table', {static: false}) tableComponent: TableComponent;


  recommendedWords: Word[];

  constructor(
    private dataService: DataService
  ) { }

  ngOnInit(): void {

  }

  getNewRecommendedWords(tiles: string) {
    this.dataService.scrabble.wordRecommendation(tiles)
      .subscribe(
      (response: {words: Word[]}) => {
        this.recommendedWords = response.words.sort((a, b) => a.score < b.score ? 1 : -1);
    });
  }

}

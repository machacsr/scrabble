import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ApiService } from './api.service';

export class Word {
  id: number;
  score: number;
  value: string;
}

export class MoveRequest {
  move: {
    rowIndex: number,
    columnIndex: string
  }
  character: string
}

export class PullResponse {
  tiles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    private apiService: ApiService
  ) { }

  scrabble = {
    wordRecommendation: (parameter: string): Observable<string> => {
      return this.apiService.get('/api/scrabble/word-recommendation/' + parameter);
    },
    move: (body: MoveRequest): Observable<Word[]> => {
      return this.apiService.post('/api/scrabble/move', body);
    },
    pull: (): Observable<PullResponse> => {
      return this.apiService.get('/api/scrabble/pull');
    },
    newGame: (): Observable<any> => {
      return this.apiService.put('/api/scrabble/new-game');
    }
  };
}

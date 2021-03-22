import { Component, OnInit } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-tyrercuzicktest',
  templateUrl: './tyrercuzicktest.component.html',
  styleUrls: ['./tyrercuzicktest.component.scss']
})
export class TyrerCuzickTestComponent {
  isProcessing = false;

  constructor(protected http: HttpClient) {}

  startTCProcess(): void {
    this.isProcessing = true;

    const url = SERVER_API_URL + 'api/tyrercuzick/trigger-process';
    this.http.get(url, { observe: 'response' }).subscribe(response => {
      this.isProcessing = false;
    });
  }
}

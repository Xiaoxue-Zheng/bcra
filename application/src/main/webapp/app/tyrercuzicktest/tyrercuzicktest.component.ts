import { Component, OnInit } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-tyrercuzicktest',
  templateUrl: './tyrercuzicktest.component.html',
  styleUrls: ['./tyrercuzicktest.component.scss']
})
export class TyrerCuzickTestComponent {
  isRunningTCProcess = false;
  isRunningTCExtract = false;

  isTCProcessError = false;
  isTCExtractError = false;

  constructor(protected http: HttpClient) {}

  startTCProcess(): void {
    this.isRunningTCProcess = true;
    this.isTCProcessError = false;

    const url = SERVER_API_URL + 'api/tyrercuzick/trigger-process';
    this.http.get(url, { observe: 'response' }).subscribe(
      response => {
        this.isRunningTCProcess = false;
      },
      error => {
        this.isTCProcessError = true;
        this.isRunningTCProcess = false;
      }
    );
  }

  startTCExtract(): void {
    this.isRunningTCExtract = true;
    this.isTCExtractError = false;

    const url = SERVER_API_URL + 'api/tyrercuzick/trigger-extract-test';
    this.http.get(url, { observe: 'response' }).subscribe(
      response => {
        this.isRunningTCExtract = false;
      },
      error => {
        this.isTCExtractError = true;
        this.isRunningTCExtract = false;
      }
    );
  }
}

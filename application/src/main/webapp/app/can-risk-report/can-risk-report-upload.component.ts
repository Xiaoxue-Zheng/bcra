import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CanRiskReportService } from './can-risk-report.service';
import { testResultsProcessor } from 'src/test/javascript/jest.conf';
import { fileURLToPath } from 'url';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-can-risk-report',
  templateUrl: './can-risk-report-upload.component.html',
  styleUrls: ['./can-risk-report-upload.component.scss']
})
export class CanRiskReportUploadComponent {
  INVALID_STUDY_ID_MESSAGE =
    'The Study ID found in this report filename does not exist or has already been associated with another CanRisk report.';
  FILE_SIZE_TOO_LARGE_MESSAGE = 'The selected file is too large to be uploaded. (10mb maximum)';
  MAX_FILE_SIZE = 10 * 1024 * 1024; // 10mb

  canRiskReports = [];
  isError = false;
  error: any = <any>{};

  constructor(protected canRiskReportService: CanRiskReportService, protected http: HttpClient, protected router: Router) {}

  getStudyId(canRiskReport) {
    return canRiskReport.name.replace('.pdf', '');
  }

  removeCanRiskReport(canRiskReport) {
    this.removeFileFromFileSelector(canRiskReport);
    this.canRiskReports = this.canRiskReports.filter(e => e.name !== canRiskReport.name);
  }

  removeFileFromFileSelector(canRiskReport) {
    const dt = new DataTransfer();
    const fileSelector = <HTMLInputElement>document.getElementById('fileSelector');

    const files = fileSelector.files;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (file.name !== canRiskReport.name) {
        dt.items.add(file);
      }
      fileSelector.files = dt.files;
    }
  }

  canUploadCanRiskReports() {
    if (this.canRiskReports.length < 1) {
      return false;
    }

    for (const canRiskReport of this.canRiskReports) {
      if (canRiskReport.inError) {
        return false;
      }
    }

    return true;
  }

  selectCanRiskReports(selectedFiles) {
    this.addSelectedFileToCanRiskReports(selectedFiles, 0);
  }

  addSelectedFileToCanRiskReports(selectedFiles, index) {
    if (index < selectedFiles.length) {
      const selectedFile = selectedFiles[index];
      const studyCode = this.getStudyId(selectedFile);
      this.canRiskReportService.isStudyIdAvailable(studyCode).subscribe(response => {
        const self = this;
        const fr = new FileReader();
        fr.onload = function(event) {
          const canRiskUploadFile = {
            name: selectedFile.name,
            inError: !response.body,
            error: '',
            file: selectedFile,
            fileSize: parseInt(selectedFile.size, 10)
          };

          if (canRiskUploadFile.inError || canRiskUploadFile.fileSize > self.MAX_FILE_SIZE) {
            if (canRiskUploadFile.fileSize > self.MAX_FILE_SIZE) {
              canRiskUploadFile.inError = true;
              canRiskUploadFile.error = self.FILE_SIZE_TOO_LARGE_MESSAGE;
            } else {
              canRiskUploadFile.error = self.INVALID_STUDY_ID_MESSAGE;
            }
          }

          self.canRiskReports.push(canRiskUploadFile);
          self.addSelectedFileToCanRiskReports(selectedFiles, index + 1);
        };

        fr.readAsBinaryString(selectedFile);
      });
    }
  }

  uploadCanRiskReports() {
    this.isError = false;

    const canRiskReportsDTO = [];
    for (const reportRow of this.canRiskReports) {
      canRiskReportsDTO.push({
        filename: reportRow.name,
        file: reportRow.file
      });
    }

    this.uploadNextCanRiskReport(canRiskReportsDTO, 0);
  }

  uploadNextCanRiskReport(reports, index) {
    if (index < reports.length) {
      this.canRiskReportService.uploadCanRiskReportFile(reports[index]).subscribe(
        success => {
          this.removeCanRiskReport(reports[index]);
          this.uploadNextCanRiskReport(reports, index + 1);
        },
        error => {
          this.isError = true;
          this.error = error;
        }
      );
    } else {
      if (!this.isError) {
        this.router.navigate(['/can-risk-report']);
      }
    }
  }

  countBytesOfString(string) {
    return encodeURI(string).split(/%..|./).length - 1;
  }
}

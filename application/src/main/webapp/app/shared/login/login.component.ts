import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';

@Component({
  selector: 'jhi-login-modal',
  templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
  authenticationError: boolean;
  authenticationErrorMessage: string;
  twoFactorAuthentication: boolean;
  showFailedPinValidationAlert: boolean;
  failedPinValidationAttempts: number;

  loginForm = this.fb.group({
    username: [''],
    password: [''],
    rememberMe: [false]
  });

  pinForm = this.fb.group({
    pin: ['']
  });

  constructor(
    private eventManager: JhiEventManager,
    private loginService: LoginService,
    private stateStorageService: StateStorageService,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private router: Router,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}

  ngAfterViewInit() {
    setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
  }

  cancel() {
    this.authenticationError = false;
    this.authenticationErrorMessage = '';
    this.twoFactorAuthentication = false;
    this.showFailedPinValidationAlert = false;
    this.failedPinValidationAttempts = 0;
    this.loginForm.patchValue({
      username: '',
      password: ''
    });
    this.pinForm.patchValue({
      pin: ''
    });
    this.activeModal.dismiss('cancel');
  }

  login() {
    this.loginService.twoFactorAuthInit(this.loginForm.get('username').value).then(res => {
      this.authenticationError = false;
      if (res) {
        this.twoFactorAuthentication = true;
      } else {
        this.loginService
          .login({
            username: this.loginForm.get('username').value,
            password: this.loginForm.get('password').value,
            rememberMe: this.loginForm.get('rememberMe').value
          })
          .then((response: any) => {
            this.loginSuccess();
          })
          .catch(error => {
            this.loginFail(error);
          });
      }
    });
  }

  loginSuccess() {
    this.authenticationError = false;
    this.activeModal.dismiss('login success');
    if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
      this.router.navigate(['']);
    }

    this.eventManager.broadcast({
      name: 'authenticationSuccess',
      content: 'Sending Authentication Success'
    });

    // previousState was set in the authExpiredInterceptor before being redirected to login modal.
    // since login is successful, go to stored previousState and clear previousState
    const redirect = this.stateStorageService.getUrl();
    if (redirect) {
      this.stateStorageService.storeUrl(null);
      this.router.navigateByUrl(redirect);
    }
  }

  loginFail(error) {
    this.authenticationError = true;
    this.authenticationErrorMessage =
      error.error && error.error.message ? error.error.message : 'Please check your credentials and try again.';
  }

  validatePin() {
    if (this.failedPinValidationAttempts === 5) {
      this.authenticationError = true;
      this.authenticationErrorMessage = 'Login is not permitted. User has had five failed pin validation attempts';
      setTimeout(() => this.cancelPinValidation(), 5000);
    } else {
      this.loginService
        .login({
          username: this.loginForm.get('username').value,
          password: this.loginForm.get('password').value,
          pin: this.pinForm.get('pin').value
        })
        .then((response: any) => {
          console.log(response);
          this.authenticationError = false;
          if (response !== null && response.authenticated !== null && !response.authenticated) {
            this.showFailedPinValidationAlert = true;
            this.failedPinValidationAttempts = response.failedAttempts;
          } else {
            this.showFailedPinValidationAlert = false;
            this.loginSuccess();
          }
        })
        .catch(error => {
          this.loginFail(error);
        });
    }
  }

  cancelPinValidation() {
    this.twoFactorAuthentication = false;
    this.showFailedPinValidationAlert = false;
    this.authenticationError = false;
  }

  register() {
    this.activeModal.dismiss('to state register');
    this.router.navigate(['/register']);
  }

  requestResetPassword() {
    this.activeModal.dismiss('to state requestReset');
    this.router.navigate(['/reset', 'request']);
  }
}

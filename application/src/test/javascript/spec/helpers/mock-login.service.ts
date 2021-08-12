import { SpyObject } from './spyobject';
import { LoginService } from 'app/core/login/login.service';
import Spy = jasmine.Spy;

export class MockLoginService extends SpyObject {
  loginSpy: Spy;
  logoutSpy: Spy;
  registerSpy: Spy;
  requestResetPasswordSpy: Spy;
  cancelSpy: Spy;
  twoFactorAuthInitSpy;

  constructor() {
    super(LoginService);
    this.setLoginSpy({});
    this.setTwoFactorAuthInitSpy({});
    this.logoutSpy = this.spy('logout').andReturn(this);
    this.registerSpy = this.spy('register').andReturn(this);
    this.requestResetPasswordSpy = this.spy('requestResetPassword').andReturn(this);
    this.cancelSpy = this.spy('cancel').andReturn(this);
  }

  setLoginSpy(json: any) {
    this.loginSpy = this.spy('login').andReturn(Promise.resolve(json));
  }

  setTwoFactorAuthInitSpy(res: any) {
    this.twoFactorAuthInitSpy = this.spy('twoFactorAuthInit').andReturn(Promise.resolve(res));
  }

  setResponse(json: any): void {
    this.setLoginSpy(json);
  }

  setTwoFactorAuthInitResponse(res: any): void {
    this.setTwoFactorAuthInitSpy(res);
  }
}

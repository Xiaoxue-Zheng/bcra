import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RiskFactorComponentsPage, RiskFactorDeleteDialog, RiskFactorUpdatePage } from './risk-factor.page-object';

const expect = chai.expect;

describe('RiskFactor e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let riskFactorComponentsPage: RiskFactorComponentsPage;
  let riskFactorUpdatePage: RiskFactorUpdatePage;
  let riskFactorDeleteDialog: RiskFactorDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RiskFactors', async () => {
    await navBarPage.goToEntity('risk-factor');
    riskFactorComponentsPage = new RiskFactorComponentsPage();
    await browser.wait(ec.visibilityOf(riskFactorComponentsPage.title), 5000);
    expect(await riskFactorComponentsPage.getTitle()).to.eq('Risk Factors');
    await browser.wait(ec.or(ec.visibilityOf(riskFactorComponentsPage.entities), ec.visibilityOf(riskFactorComponentsPage.noResult)), 1000);
  });

  it('should load create RiskFactor page', async () => {
    await riskFactorComponentsPage.clickOnCreateButton();
    riskFactorUpdatePage = new RiskFactorUpdatePage();
    expect(await riskFactorUpdatePage.getPageTitle()).to.eq('Create or edit a Risk Factor');
    await riskFactorUpdatePage.cancel();
  });

  it('should create and save RiskFactors', async () => {
    const nbButtonsBeforeCreate = await riskFactorComponentsPage.countDeleteButtons();

    await riskFactorComponentsPage.clickOnCreateButton();

    await promise.all([riskFactorUpdatePage.setFactorInput('5')]);

    expect(await riskFactorUpdatePage.getFactorInput()).to.eq('5', 'Expected factor value to be equals to 5');

    await riskFactorUpdatePage.save();
    expect(await riskFactorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await riskFactorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RiskFactor', async () => {
    const nbButtonsBeforeDelete = await riskFactorComponentsPage.countDeleteButtons();
    await riskFactorComponentsPage.clickOnLastDeleteButton();

    riskFactorDeleteDialog = new RiskFactorDeleteDialog();
    expect(await riskFactorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Risk Factor?');
    await riskFactorDeleteDialog.clickOnConfirmButton();

    expect(await riskFactorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

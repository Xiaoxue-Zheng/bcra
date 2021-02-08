import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RiskComponentsPage, RiskDeleteDialog, RiskUpdatePage } from './risk.page-object';

const expect = chai.expect;

describe('Risk e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let riskComponentsPage: RiskComponentsPage;
  let riskUpdatePage: RiskUpdatePage;
  let riskDeleteDialog: RiskDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Risks', async () => {
    await navBarPage.goToEntity('risk');
    riskComponentsPage = new RiskComponentsPage();
    await browser.wait(ec.visibilityOf(riskComponentsPage.title), 5000);
    expect(await riskComponentsPage.getTitle()).to.eq('Risks');
    await browser.wait(ec.or(ec.visibilityOf(riskComponentsPage.entities), ec.visibilityOf(riskComponentsPage.noResult)), 1000);
  });

  it('should load create Risk page', async () => {
    await riskComponentsPage.clickOnCreateButton();
    riskUpdatePage = new RiskUpdatePage();
    expect(await riskUpdatePage.getPageTitle()).to.eq('Create or edit a Risk');
    await riskUpdatePage.cancel();
  });

  it('should create and save Risks', async () => {
    const nbButtonsBeforeCreate = await riskComponentsPage.countDeleteButtons();

    await riskComponentsPage.clickOnCreateButton();

    await promise.all([
      riskUpdatePage.setLifetimeRiskInput('5'),
      riskUpdatePage.setProbNotBcraInput('5'),
      riskUpdatePage.setProbBcra1Input('5'),
      riskUpdatePage.setProbBcra2Input('5')
    ]);

    expect(await riskUpdatePage.getLifetimeRiskInput()).to.eq('5', 'Expected lifetimeRisk value to be equals to 5');
    expect(await riskUpdatePage.getProbNotBcraInput()).to.eq('5', 'Expected probNotBcra value to be equals to 5');
    expect(await riskUpdatePage.getProbBcra1Input()).to.eq('5', 'Expected probBcra1 value to be equals to 5');
    expect(await riskUpdatePage.getProbBcra2Input()).to.eq('5', 'Expected probBcra2 value to be equals to 5');

    await riskUpdatePage.save();
    expect(await riskUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await riskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Risk', async () => {
    const nbButtonsBeforeDelete = await riskComponentsPage.countDeleteButtons();
    await riskComponentsPage.clickOnLastDeleteButton();

    riskDeleteDialog = new RiskDeleteDialog();
    expect(await riskDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Risk?');
    await riskDeleteDialog.clickOnConfirmButton();

    expect(await riskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

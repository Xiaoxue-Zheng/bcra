import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RiskAssessmentResultComponentsPage,
  /* RiskAssessmentResultDeleteDialog, */
  RiskAssessmentResultUpdatePage
} from './risk-assessment-result.page-object';

const expect = chai.expect;

describe('RiskAssessmentResult e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let riskAssessmentResultComponentsPage: RiskAssessmentResultComponentsPage;
  let riskAssessmentResultUpdatePage: RiskAssessmentResultUpdatePage;
  /* let riskAssessmentResultDeleteDialog: RiskAssessmentResultDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RiskAssessmentResults', async () => {
    await navBarPage.goToEntity('risk-assessment-result');
    riskAssessmentResultComponentsPage = new RiskAssessmentResultComponentsPage();
    await browser.wait(ec.visibilityOf(riskAssessmentResultComponentsPage.title), 5000);
    expect(await riskAssessmentResultComponentsPage.getTitle()).to.eq('Risk Assessment Results');
    await browser.wait(
      ec.or(ec.visibilityOf(riskAssessmentResultComponentsPage.entities), ec.visibilityOf(riskAssessmentResultComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RiskAssessmentResult page', async () => {
    await riskAssessmentResultComponentsPage.clickOnCreateButton();
    riskAssessmentResultUpdatePage = new RiskAssessmentResultUpdatePage();
    expect(await riskAssessmentResultUpdatePage.getPageTitle()).to.eq('Create or edit a Risk Assessment Result');
    await riskAssessmentResultUpdatePage.cancel();
  });

  /* it('should create and save RiskAssessmentResults', async () => {
        const nbButtonsBeforeCreate = await riskAssessmentResultComponentsPage.countDeleteButtons();

        await riskAssessmentResultComponentsPage.clickOnCreateButton();

        await promise.all([
            riskAssessmentResultUpdatePage.setFilenameInput('filename'),
            riskAssessmentResultUpdatePage.participantSelectLastOption(),
            riskAssessmentResultUpdatePage.individualRiskSelectLastOption(),
            riskAssessmentResultUpdatePage.populationRiskSelectLastOption(),
        ]);

        expect(await riskAssessmentResultUpdatePage.getFilenameInput()).to.eq('filename', 'Expected Filename value to be equals to filename');

        await riskAssessmentResultUpdatePage.save();
        expect(await riskAssessmentResultUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await riskAssessmentResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last RiskAssessmentResult', async () => {
        const nbButtonsBeforeDelete = await riskAssessmentResultComponentsPage.countDeleteButtons();
        await riskAssessmentResultComponentsPage.clickOnLastDeleteButton();

        riskAssessmentResultDeleteDialog = new RiskAssessmentResultDeleteDialog();
        expect(await riskAssessmentResultDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Risk Assessment Result?');
        await riskAssessmentResultDeleteDialog.clickOnConfirmButton();

        expect(await riskAssessmentResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

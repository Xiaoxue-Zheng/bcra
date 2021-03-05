import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudyIdComponentsPage, StudyIdUpdatePage } from './study-id.page-object';

const expect = chai.expect;

describe('StudyId e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studyIdComponentsPage: StudyIdComponentsPage;
  let studyIdUpdatePage: StudyIdUpdatePage;

  async function waitForStudyIdPage() {
    await browser.wait(ec.or(ec.visibilityOf(studyIdComponentsPage.entities), ec.visibilityOf(studyIdComponentsPage.noResult)), 2500);
  }

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StudyIds', async () => {
    await navBarPage.goToEntity('study-id');
    studyIdComponentsPage = new StudyIdComponentsPage();
    await browser.wait(ec.visibilityOf(studyIdComponentsPage.title), 5000);
    expect(await studyIdComponentsPage.getTitle()).to.eq('Study IDs');
    await waitForStudyIdPage();
  });

  it('should load create StudyId page', async () => {
    await studyIdComponentsPage.clickOnCreateButton();
    studyIdUpdatePage = new StudyIdUpdatePage();
    expect(await studyIdUpdatePage.getPageTitle()).to.eq('Enter a list of comma separated study codes');
    await studyIdUpdatePage.cancel();
  });

  it('should create and save StudyIds', async () => {
    const studyIdsBeforeCreate = await studyIdComponentsPage.countStudyIds();

    await studyIdComponentsPage.clickOnCreateButton();

    const studyId = 'TST_SINGLE_' + studyIdsBeforeCreate;
    await studyIdUpdatePage.setCodeInput(studyId);

    expect(await studyIdUpdatePage.getCodeInput()).to.eq(studyId, 'Expected Code value to be equals to ' + studyId);

    await studyIdUpdatePage.save();
    await waitForStudyIdPage();

    const studyIdsAfterCreate = await studyIdComponentsPage.countStudyIds();
    expect(studyIdsAfterCreate).to.eq(studyIdsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should create multiple StudyIds', async () => {
    const studyIdsBeforeCreate = await studyIdComponentsPage.countStudyIds();

    await studyIdComponentsPage.clickOnCreateButton();

    const studyIdString =
      'TST_MULTIPLE_0_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_1_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_2_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_3_' +
      studyIdsBeforeCreate;
    await studyIdUpdatePage.setCodeInput(studyIdString);

    expect(await studyIdUpdatePage.getCodeInput()).to.eq(studyIdString, 'Expected Code value to be equals to ' + studyIdString);

    await studyIdUpdatePage.save();
    await waitForStudyIdPage();

    const studyIdsAfterCreate = await studyIdComponentsPage.countStudyIds();
    expect(studyIdsAfterCreate).to.eq(studyIdsBeforeCreate + 4, 'Expected four more entries in the table');
  });

  it('should fail to create a StudyId if there are duplicates', async () => {
    const studyIdsBeforeCreate = await studyIdComponentsPage.countStudyIds();

    await studyIdComponentsPage.clickOnCreateButton();

    const studyId = 'TST_DUPLICATE_' + studyIdsBeforeCreate;
    await studyIdUpdatePage.setCodeInput(studyId);

    expect(await studyIdUpdatePage.getCodeInput()).to.eq(studyId, 'Expected Code value to be equals to ' + studyId);

    await studyIdUpdatePage.save();
    await waitForStudyIdPage();

    await studyIdComponentsPage.clickOnCreateButton();
    await studyIdUpdatePage.setCodeInput(studyId);

    await studyIdUpdatePage.save();
    await studyIdUpdatePage.cancel();
    await waitForStudyIdPage();

    const studyIdsAfterCreate = await studyIdComponentsPage.countStudyIds();
    expect(studyIdsAfterCreate).to.eq(studyIdsBeforeCreate + 1, 'Expected only one more entry in the table');
  });

  it('should fail to create multiple StudyIds', async () => {
    const studyIdsBeforeCreate = await studyIdComponentsPage.countStudyIds();

    await studyIdComponentsPage.clickOnCreateButton();

    const studyIdString =
      'TST_MULTIPLE_DUPLICATE_0_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_DUPLICATE_1_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_DUPLICATE_2_' +
      studyIdsBeforeCreate +
      ',' +
      'TST_MULTIPLE_DUPLICATE_3_' +
      studyIdsBeforeCreate;
    await studyIdUpdatePage.setCodeInput(studyIdString);

    expect(await studyIdUpdatePage.getCodeInput()).to.eq(studyIdString, 'Expected Code value to be equals to ' + studyIdString);

    await studyIdUpdatePage.save();
    await waitForStudyIdPage();

    await studyIdComponentsPage.clickOnCreateButton();
    await studyIdUpdatePage.setCodeInput(studyIdString);

    await studyIdUpdatePage.save();
    await studyIdUpdatePage.cancel();
    await waitForStudyIdPage();

    const studyIdsAfterCreate = await studyIdComponentsPage.countStudyIds();
    expect(studyIdsAfterCreate).to.eq(studyIdsBeforeCreate + 4, 'Expected only 4 more study ids');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

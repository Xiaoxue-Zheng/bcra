import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../page-objects/jhi-page-objects';

import { ParticipantComponentsPage } from './participants.page-object';

const expect = chai.expect;

describe('StudyId e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let participantComponentsPage: ParticipantComponentsPage;

  async function waitForParticipantPage() {
    await browser.wait(ec.or(ec.visibilityOf(participantComponentsPage.searchButton)), 2500);
  }

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.twoFactorSignIn('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.participantsMenu), 5000);
  });

  it('should load participant', async () => {
    await navBarPage.clickOnParticipants();
    participantComponentsPage = new ParticipantComponentsPage();
    await browser.wait(ec.visibilityOf(participantComponentsPage.title), 5000);
    expect(await participantComponentsPage.getTitle()).to.eq('Participants');
    await waitForParticipantPage();
  });

  it('should load participant when search by StudyId', async () => {
    participantComponentsPage.setStudyId('test-001');
    await participantComponentsPage.clickSearchButton();
    const participantCnt = await participantComponentsPage.countParticipants();
    expect(participantCnt).to.eq(1);
  });

  it('should load participant when search by Date of Birth', async () => {
    participantComponentsPage.setDateOfBirth('1990-09-15');
    participantComponentsPage.clickSearchButton();
    const participantCnt = await participantComponentsPage.countParticipants();
    expect(participantCnt).to.eq(1);
  });

  it('should load participant when search by status', async () => {
    participantComponentsPage.setSubmittedStatus();
    participantComponentsPage.clickSearchButton();
    const participantCnt = await participantComponentsPage.countParticipants();
    expect(participantCnt).to.eq(1);
  });

  it('should load participant when click Clear', async () => {
    participantComponentsPage.setSubmittedStatus();
    participantComponentsPage.setStudyId('test-001');
    participantComponentsPage.setDateOfBirth('1990-09-15');
    await participantComponentsPage.clickClearButton();
    expect(await participantComponentsPage.getStudyId()).to.eq('');
    expect(await participantComponentsPage.getDateOfBirth()).to.eq('');
    expect(await participantComponentsPage.getStatus()).to.eq('null');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

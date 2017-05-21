package uitest

import com.leapstack.insurance.version.BuildInfo._

class MainPageUiSpec extends BaseUiSpec {

  test("application version") {
    // when
    mainPage.open()

    // then
    mainPage.getVersionString should be(s"Build ${buildSha.substring(0, 6)}, $buildDate")
  }
}

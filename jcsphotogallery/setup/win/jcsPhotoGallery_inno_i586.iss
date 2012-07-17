; -- JcsPhotoGallery.iss for i586 --

[Setup]
AppName=JcsPhotoGallery
AppVersion=1.1.1
AppPublisher=JcsPhotoGallery
AppPublisherURL=http://www.dancioi.net/projects/jcsphotogallery/
AppSupportURL=http://code.google.com/p/jcsphotogallery/
AppUpdatesURL=http://code.google.com/p/jcsphotogallery/
DefaultDirName={pf}\JcsPhotoGallery
DefaultGroupName=JcsPhotoGallery
UninstallDisplayIcon={app}\JcsPhotoGallery.exe
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:JcsPhotoGallery installer
OutputBaseFilename=JcsPhotoGallery_App_1.1.1_setup_i586.exe
LicenseFile=COPYING.txt

[Files]
Source: "jre-7u5-windows-i586.exe"; DestDir: "{app}"
Source: "JcsPhotoGallery.exe"; DestDir: "{app}"
Source: "Readme.txt"; DestDir: "{app}"; Flags: isreadme
Source: "help\*"; DestDir: "{app}\help"

[Icons]
Name: "{group}\JcsPhotoGallery"; Filename: "{app}\JcsPhotoGallery.exe"

[Run]
Filename: "{app}\jre-7u5-windows-i586"; Parameters: ""; Check: IsJREInstalled; Flags: runhidden shellexec waituntilterminated
Filename: "{app}\JcsPhotoGallery"; Parameters: ""; Flags: runhidden shellexec waituntilterminated

[INI]
Filename: {app}\configs.ini; Section: ROOT; Key: CONFIGS_PATH; String: {code:addConfig}

[Code]
function IsJREInstalled: boolean;
begin
Result := RegKeyExists(HKEY_LOCAL_MACHINE,'SOFTWARE\JavaSoft\Java Runtime Environment\1.7')
end;

function addConfig(Param: String): string;
begin
CreateDir(ExpandConstant('{userappdata}\JcsPhotoGallery'));
result := ExpandConstant('{userappdata}\JcsPhotoGallery\configs.cfg');
end;
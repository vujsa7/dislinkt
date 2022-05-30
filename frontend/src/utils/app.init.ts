import { KeycloakService } from 'keycloak-angular';

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: 'http://localhost:8080',
            realm: 'dislinkt',
            clientId: 'frontend-application',
          },
          loadUserProfileAtStartUp: true,
          initOptions: {
            onLoad: 'check-sso',
            checkLoginIframe: true
          },
          bearerExcludedUrls: ['/assets', '/feed'],
        });
        resolve(resolve);
      } catch (error) {
        reject(error);
      }
    });
  };
} 
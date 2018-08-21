package com.zsy.sso.server_sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthentizationServerConfig 
	extends AuthorizationServerConfigurerAdapter
{
	//给应用a与应用b发令牌
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("client1")
							.secret("client1Secrect1")
							.authorizedGrantTypes("authorization_code","refresh_token")//类型配置错了.报错:error="invalid_grant", error_description="A redirect_uri can only be used by implicit or authorization_code grant types."
							.scopes("all")
							.and()
							.withClient("client2")
							.secret("client2Secrect2")
							.authorizedGrantTypes("authorization_code","refresh_token")
							.scopes("all");
	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(JwtAccessTokenConverter());//accessToken没有配置,报错:Could not obtain user details from token
	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()");//权限表达式,访问下面token key的时候需要认证
		
		//最后发回客户端是一个jwt,jwt会用一个密钥来签名,下面使用zsy,客户使用这个密钥来解析tokenkey
		//tokenKeyAccess默认是denyAll()
	}
	//发令牌
	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(JwtAccessTokenConverter());
	}
	//用密钥加密签名
	@Bean
	public JwtAccessTokenConverter JwtAccessTokenConverter() {
		JwtAccessTokenConverter conver = new JwtAccessTokenConverter();
		conver.setSigningKey("zsy");//zsy这个是token key
		return conver;
	}
}

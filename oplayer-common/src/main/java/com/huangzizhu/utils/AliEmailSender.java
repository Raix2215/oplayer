package com.huangzizhu.utils;

import com.aliyun.tea.TeaException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliEmailSender {
    public static com.aliyun.dm20151123.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        config.endpoint = "dm.aliyuncs.com";
        return new com.aliyun.dm20151123.Client(config);
    }

    public static void send(String address, String body) throws Exception {
        com.aliyun.dm20151123.Client client = AliEmailSender.createClient();
        com.aliyun.dm20151123.models.SingleSendMailRequest singleSendMailRequest = new com.aliyun.dm20151123.models.SingleSendMailRequest()
                .setAccountName("admin@mail.huangzizhu.com")
                .setAddressType(1)
                .setTagName("")
                .setReplyToAddress(false)
                .setToAddress(address)
                .setSubject("邮箱验证码")
                .setTextBody(body)
                .setFromAlias("admin")
                .setReplyAddress("")
                .setReplyAddressAlias("")
                .setClickTrace("0")
                .setUnSubscribeLinkType("")
                .setUnSubscribeFilterLevel("");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            client.singleSendMailWithOptions(singleSendMailRequest, runtime);
        } catch (TeaException error) {
            log.error(error.getMessage());
            log.error((String) error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error(error.getMessage());
            log.error((String) error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}






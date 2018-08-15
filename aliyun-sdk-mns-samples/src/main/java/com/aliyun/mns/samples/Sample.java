/*   Copyright (C) 2015 Alibaba Cloud Computing. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.aliyun.mns.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.aliyun.mns.client.AsyncCallback;
import com.aliyun.mns.client.AsyncResult;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.BatchDeleteException;
import com.aliyun.mns.common.BatchSendException;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.ErrorMessageResult;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.QueueMeta;

public class Sample {
    private MNSClient client = null;
 
    private static String QUEUE_NAME1 = "java-test-abc";
    private static String QUEUE_NAME2 = "java-test-efg";

    public Sample() {
        CloudAccount account = new CloudAccount(
                ServiceSettings.getMNSAccessKeyId(),
                ServiceSettings.getMNSAccessKeySecret(),
                ServiceSettings.getMNSAccountEndpoint());
        client = account.getMNSClient();
    }

    public void queueOperators() {
        // 两种方法创建队列
        try {
            // 创建队列一
            QueueMeta meta1 = new QueueMeta();
            meta1.setQueueName(QUEUE_NAME1);
            meta1.setPollingWaitSeconds(15);
            meta1.setMaxMessageSize(2048L);

            CloudQueue queue1 = client.createQueue(meta1);
            System.out.println("Queue1 URL: " + queue1.getQueueURL());

            // 创建队列二
            QueueMeta meta2 = new QueueMeta();
            meta2.setQueueName(QUEUE_NAME2);
            meta2.setPollingWaitSeconds(15);
            meta2.setMaxMessageSize(2048L);

            CloudQueue queue2 = client.getQueueRef(QUEUE_NAME2);
            String queueURL = queue2.create(meta2);
            System.out.println("Queeu2 URL: " + queueURL);

            // 属性相同，可以重复创建
            queue1 = client.createQueue(meta1);
            String queueURL2 = queue2.create(meta2);

            System.out.println("Queue1 URL: " + queue1.getQueueURL());
            System.out.println("Queeu2 URL: " + queueURL2);
        } catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }

        // 重复创建数据不同，409错误，错误码：QueueAlreadyExist
        try {
            QueueMeta meta1 = new QueueMeta();
            meta1.setQueueName(QUEUE_NAME1);
            meta1.setPollingWaitSeconds(15);
            meta1.setMaxMessageSize(2048L);
            // 设置不同的DelaySeconds
            meta1.setDelaySeconds(30L);

            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            queue.create(meta1);
        } catch (ServiceException ex) {
            System.out.println("CreateQueue: " + QUEUE_NAME1 + ", but "
                    + ex.getErrorCode());
        }

        try {
            QueueMeta meta2 = new QueueMeta();
            meta2.setQueueName(QUEUE_NAME2);
            meta2.setPollingWaitSeconds(15);
            meta2.setMaxMessageSize(2048L);
            // 设置不同的DelaySeconds
            meta2.setDelaySeconds(30L);

            client.createQueue(meta2);
        } catch (ServiceException ex) {
            System.out.println("CreateQueue: " + QUEUE_NAME2 + ", but "
                    + ex.getErrorCode());
        }

        // 遍历队列
        String marker = null;
        do {
            PagingListResult<String> list = new PagingListResult<String>();
            try {
                list = client.listQueueURL("java-test-", marker, 1);
            } catch (ClientException ex) {
                // 错误处理
                ex.printStackTrace();
            } catch (ServiceException ex) {
                // 错误处理
                ex.printStackTrace();
            }

            List<String> queues = list.getResult();
            marker = list.getMarker();

            System.out.println("Result:");
            for (String queue : queues) {
                System.out.println(queue);
            }
        } while (marker != null && marker != "");

        // 获取队列属性
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            QueueMeta queueMeta = queue.getAttributes();
            System.out.println(queueMeta.getDelaySeconds());
            System.out.println(queueMeta.getActiveMessages());
            System.out.println(queueMeta.getDelaySeconds());
        } catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }

        // 修改队列属性
        try {
            QueueMeta newMeta = new QueueMeta();
            newMeta.setQueueName(QUEUE_NAME1);
            newMeta.setDelaySeconds(30L);

            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            queue.setAttributes(newMeta);

            QueueMeta queueMeta = queue.getAttributes();
            System.out.println(queueMeta.getDelaySeconds());
        } catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }

        // 删除队列
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            queue.delete();
            // 幂等性
            queue.delete();

            // 获取不存在的队列属性失败
            try {
                QueueMeta queueMeta = queue.getAttributes();
                // 下面一行不被执行
                System.out.println(queueMeta.getQueueName());
            } catch (ServiceException ex) {
                System.out.println(ex.getErrorCode());
            }

            CloudQueue queue2 = client.getQueueRef(QUEUE_NAME2);
            queue2.delete();
        } catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }
    }

    // 使用同步接口
    public void messageOperators() {
        try {
            // 创建队列
            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            String s = queue.create();
            System.out.println(s);

            // 发送消息
            Message message = new Message();
            message.setMessageBody("message_body");
            Message putMsg = queue.putMessage(message);
            System.out.println("PutMessage has MsgId: " + putMsg.getMessageId());

            // 查看消息
            Message peekMsg = queue.peekMessage();
            System.out.println("PeekMessage has MsgId: "
                    + peekMsg.getMessageId());
            System.out.println("PeekMessage Body: "
                    + peekMsg.getMessageBodyAsString());

            // 获取消息
            Message popMsg = queue.popMessage();
            System.out.println("PopMessage Body: "
                    + popMsg.getMessageBodyAsString());

            // 更改消息可见时间
            String receiptHandle = popMsg.getReceiptHandle();
            int visibilityTimeout = 100;
            String rh = queue.changeMessageVisibilityTimeout(receiptHandle,
                    visibilityTimeout);
            System.out.println("ReceiptHandle:" + rh);

            // 获取消息为空
            Message popMsg2 = queue.popMessage();
            if (popMsg2 == null) {
                System.out.println("No Message popped!");
            }

            // 删除消息
            queue.deleteMessage(rh);

            // 删除队列
            queue.delete();
        } catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }
    }
    
    // 使用同步批量接口
    public void messageBatchOperators() {
        try {
            // 创建队列
            CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
            queue.create();

            int batchMsgSize = 5; //不能大于16
            // 待发送的消息
            List<Message> msgs = new ArrayList<Message>();
            List<Message> asyncMsgs = new ArrayList<Message>();
            for (int i = 0; i < batchMsgSize; i++) {
                Message message = new Message();
                message.setMessageBody("message_body_" + i);
                msgs.add(message);
                
                Message asyncMsg = new Message();
                asyncMsg.setMessageBody("async_message_body_" + i);
                asyncMsgs.add(asyncMsg);
            }
            
            // 批量发送消息
            List<Message> putMsgs = queue.batchPutMessage(msgs);
            for (Message putMsg : putMsgs) {
                System.out.println("PutMessage has MsgId: " + putMsg.getMessageId());
            }
            
            // 异步批量发送消息
            AsyncCallback<List<Message>> putCallback = new AsyncCallback<List<Message>>() {

                @Override
                public void onSuccess(List<Message> result) {
                    for (Message putMsg : result) {
                        System.out.println("PutMessage has MsgId:" + putMsg.getMessageId());
                    }
                }

                @Override
                public void onFail(Exception ex) {
                    if (ex instanceof BatchSendException) {
                        List<Message> messages = ((BatchSendException) ex).getMessages();
                        for (Message msg : messages) {
                            if (msg.isErrorMessage())
                            {
                                ErrorMessageResult errorMessageDetail = msg.getErrorMessageDetail();
                                System.out.println("PutMessage Fail."
                                        + " ErrorCode: " + errorMessageDetail.getErrorCode()
                                        + " ErrorMessage: " + errorMessageDetail.getErrorMessage());
                            }
                            else
                            {
                                System.out.println(msg);
                            }
                        }
                    }
                    else {
                        System.out.println("AsyncBatchPutMessage Exception: ");
                        ex.printStackTrace();
                    }
                }                
            };
            AsyncResult<List<Message>> asyncBatchPutMessage = queue.asyncBatchPutMessage(asyncMsgs, putCallback);
            // 等待异步完成，仅是Sample中简化使用
            asyncBatchPutMessage.getResult();
            
            // 批量查看消息
            List<Message> batchPeekMessage = queue.batchPeekMessage(batchMsgSize);
            for (Message peekMsg : batchPeekMessage) {
                System.out.println("PeekMessage has MsgId:" + peekMsg.getMessageId());
            }
            
            // 异步批量查看消息
            AsyncCallback<List<Message>> peekCallback = new AsyncCallback<List<Message>>() {
                @Override
                public void onSuccess(List<Message> result) {
                    for (Message msg : result) {
                        System.out.println("AsyncBatchPeekMessage has MsgId: " + msg.getMessageId());
                    }
                }

                @Override
                public void onFail(Exception ex) {
                    System.out.println("AsyncBatchPeekMessage Exception: ");
                    ex.printStackTrace();
                }                
            };
            AsyncResult<List<Message>> asyncBatchPeekMessage = queue.asyncBatchPeekMessage(batchMsgSize, peekCallback);
            // 等待异步完成，仅是Sample中简化使用
            asyncBatchPeekMessage.getResult();
            
            List<String> receiptsToDelete = new ArrayList<String>();
            // 批量获取消息
            List<Message> batchPopMessage = queue.batchPopMessage(batchMsgSize);
            for (Message popMsg : batchPopMessage) {
                System.out.println("PopMessage has MsgId: " + popMsg.getMessageId());
                receiptsToDelete.add(popMsg.getReceiptHandle());
            }
          
            // 异步批量获取消息
            class AsyncBatchPopCallback implements AsyncCallback<List<Message> > {

                @Override
                public void onSuccess(List<Message> result) {
                    for (Message msg : result) {
                        System.out.println("AsyncBatchPopMessage has MsgId: " + msg.getMessageId());
                        receipts.add(msg.getReceiptHandle());
                    }
                }

                @Override
                public void onFail(Exception ex) {
                    System.out.println("AsyncBatchPopMessage Exception: ");
                    ex.printStackTrace();
                }
                
                public List<String> receipts = new ArrayList<String>();
            };
            AsyncBatchPopCallback popCallback = new AsyncBatchPopCallback();
            AsyncResult<List<Message>> asyncBatchPopMessage = queue.asyncBatchPopMessage(batchMsgSize, popCallback);
            // 等待异步完成，仅是Sample中简化使用
            asyncBatchPopMessage.getResult();
            
            // 删除消息
            queue.batchDeleteMessage(receiptsToDelete);
            
            AsyncCallback<Void> deleteCallback = new AsyncCallback<Void> () {

                @Override
                public void onSuccess(Void result) {
                    System.out.println("Async BatchDelete messages success!");
                }

                @Override
                public void onFail(Exception ex) {
                    if (ex instanceof BatchDeleteException) {
                        Map<String, ErrorMessageResult> errorMessages = ((BatchDeleteException) ex).getErrorMessages();
                        for (String receiptHandle : errorMessages.keySet()) {
                            ErrorMessageResult error = errorMessages.get(receiptHandle);
                            System.out.println("ReceiptHandle to delete : " + receiptHandle
                                    + ", errorcode: " + error.getErrorCode()
                                    + ", errormessage: " + error.getErrorMessage());
                        }
                    }
                    else
                    {
                        System.out.println("AsyncBatchDeleteMessage Exception: ");
                        ex.printStackTrace();
                    }
                }
                
            };
            List<String> receiptsWithDeleted = new ArrayList<String>();
            receiptsWithDeleted.addAll(popCallback.receipts);
            receiptsWithDeleted.addAll(receiptsToDelete);
            AsyncResult<Void> asyncBatchDeleteMessage = queue.asyncBatchDeleteMessage(receiptsWithDeleted, deleteCallback);
            // 等待异步完成，仅是Sample中简化使用
            asyncBatchDeleteMessage.getResult();
            
            // 删除队列
            queue.delete();
        }
        catch (ClientException ex) {
            // 错误处理
            ex.printStackTrace();
        } catch (ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }
    }
    
    public class TaskBase {
        public void waitComplete() {
            mLock.lock();
            if (!mFinished.get()) {
                try {
                    mCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mLock.unlock();
        }

        public int updateCompleteCount() {
            int num = mCount.incrementAndGet();
            if (num >= mNum) {
                mLock.lock();
                mFinished.set(true);
                mCondition.signal();
                mLock.unlock();
            }
            return num;
        }

        public int mNum;

        private final AtomicInteger mCount = new AtomicInteger(0);
        private final ReentrantLock mLock = new ReentrantLock();
        private final Condition mCondition = mLock.newCondition();
        private final AtomicBoolean mFinished = new AtomicBoolean(false);
    }

    public class ProduceTask extends TaskBase implements Runnable {

        public ProduceTask(CloudQueue queue, int sendNum) {
            mQueue = queue;
            mNum = sendNum;
        }

        public void run() {
            int hasSendNum = 0;
            while (hasSendNum++ < mNum) {
                Message message = new Message();
                message.setMessageBody("message_body_" + hasSendNum);

                SendAsyncCallback cb = new SendAsyncCallback(this);
                AsyncResult<Message> asyncPutResult = mQueue.asyncPutMessage(
                        message, cb);
                if (asyncPutResult == null) {
                    System.out.println("AsyncSendMessage Fail");
                }
            }

            waitComplete();
        }

        public CloudQueue mQueue;
    };

    protected class SendAsyncCallback implements AsyncCallback<Message> {
        public SendAsyncCallback(ProduceTask task) {
            mTask = task;
        }

        @Override
        public void onSuccess(Message result) {
            System.out.println("Send Message " + result.getMessageId());

            mTask.updateCompleteCount();
        }

        @Override
        public void onFail(Exception ex) {
            System.out.println("Send Message Fail.");
            ex.printStackTrace();

            mTask.updateCompleteCount();
        }

        private ProduceTask mTask;
    };

    protected class ConsumeTask extends TaskBase implements Runnable {
        public ConsumeTask(CloudQueue queue, int receiveNum) {
            mQueue = queue;
            mNum = receiveNum;
        }

        @Override
        public void run() {
            int receiveMsgNum = 0;
            while (receiveMsgNum++ < mNum) {
                ReceiveDeleteAsyncCallback<Message> cb = new ReceiveDeleteAsyncCallback<Message>(
                        this);
                AsyncResult<Message> asyncPopMsgResult = mQueue
                        .asyncPopMessage(cb);
                if (asyncPopMsgResult == null) {
                    System.out.println("AsyncPopMessage Fail!");
                }
            }
            waitComplete();
        }

        public CloudQueue mQueue;
    };

    static enum MessageStage {
        ReceiveStage, DeleteStage, FinishStage
    };

    protected class ReceiveDeleteAsyncCallback<T> implements AsyncCallback<T> {
        public ReceiveDeleteAsyncCallback(ConsumeTask task) {
            mTask = task;
            mStage = MessageStage.ReceiveStage;
        }

        @Override
        public void onSuccess(T msg) {
            if (mStage == MessageStage.ReceiveStage) {
                mMessage = (Message) msg;
                System.out.println("Receive Message " + mMessage.getMessageId());

                doDelete();
            } else if (mStage == MessageStage.DeleteStage) {
                System.out.println("Delete Message " + mMessage.getMessageId());

                mTask.updateCompleteCount();
            }
        }

        @Override
        public void onFail(Exception ex) {
            System.out.println("Operate Message Fail.");
            if (ex instanceof ServiceException
                    && mTask.mQueue.isMessageNotExist((ServiceException) ex)) {
                System.out.println("Stage:" + mStage);

                if (mStage == MessageStage.ReceiveStage) {
                    System.out.println("Continue to receive message.");
                    doReceive();
                } else {
                    if (mStage == MessageStage.DeleteStage) {
                        System.out.println("Message does not exist when deleting.");
                    }

                    mTask.updateCompleteCount();
                }
            } else {
                ex.printStackTrace();

                mTask.updateCompleteCount();
            }
        }

        public void doReceive() {
            mStage = MessageStage.ReceiveStage;
            mTask.mQueue
                    .asyncPopMessage((ReceiveDeleteAsyncCallback<Message>) this);
        }

        public void doDelete() {
            mStage = MessageStage.DeleteStage;
            mTask.mQueue.asyncDeleteMessage(mMessage.getReceiptHandle(),
                    (ReceiveDeleteAsyncCallback<Void>) this);
        }

        private ConsumeTask mTask;
        private MessageStage mStage;
        private Message mMessage;
    };

    // 使用异步接口
    public void multiThreadHandleMsgs() {
        // 创建队列
        CloudQueue queue = client.getQueueRef(QUEUE_NAME1);
        queue.create();

        int numOfMessages = 100;
        int produceTaskNum = 2;
        int consumeTaskNum = 5;
        assert numOfMessages % produceTaskNum == 0;
        assert numOfMessages % consumeTaskNum == 0;

        ExecutorService es = Executors.newFixedThreadPool(5);

        // send message tasks
        int msgNum2SendPerTask = numOfMessages / produceTaskNum;
        while (produceTaskNum-- > 0) {
            ProduceTask produceTask = new ProduceTask(queue, msgNum2SendPerTask);
            es.submit(produceTask);
        }

        // receive&delete message tasks
        int msgNum2ReceivePerTask = numOfMessages / consumeTaskNum;
        while (consumeTaskNum-- > 0) {
            ConsumeTask consumTask = new ConsumeTask(queue,
                    msgNum2ReceivePerTask);
            es.submit(consumTask);
        }

        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        queue.delete();
    }
    
    public void run() {
//        queueOperators();
        messageOperators();
//        messageBatchOperators();
//        multiThreadHandleMsgs();
    }
    
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.run();
        sample.close();
        
        System.exit(0);
    }
}

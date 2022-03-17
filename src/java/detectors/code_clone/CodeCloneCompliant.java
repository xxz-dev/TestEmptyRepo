/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package detectors.code_clone;

import lombok.extern.slf4j.Slf4j;
import stubs.*;

import java.nio.file.Path;
import java.util.Map;

@Slf4j
public class CodeCloneCompliant {
    // {fact rule=code-clone@v1.0 defects=0}
    public ActivityJobItem getJobFromArnAndActivityType(String activityJobArn, ActivityType activityType){
        ActivityJobItem activityJob = null;
        if (activityType == ActivityType.TRAINING) {
            activityJob = trainingJobDao.loadTrainingJob(activityJobArn);
        } else if (activityType == ActivityType.EVALUATION) {
            activityJob = evaluationJobDao.loadEvaluationJob(activityJobArn);
        } else if (activityType == ActivityType.FINETUNING) {
            activityJob = finetuningJobDao.loadFinetuningJob(activityJobArn);
        }
        return activityJob;
    }

    private boolean doesVideoStreamExistForJobCompliant(final String videoStreamArn,
                                                final String viewAngle,
                                                final String activityJobArn,
                                                final ActivityType activityType) throws Exception {
        try {
            ActivityJobItem activityJob = null;
            // Compliant: avoids using similar code fragments in the same file.
            activityJob = getJobFromArnAndActivityType(activityJobArn, activityType);
            if (activityJob == null) {
                throw new Exception("Unexpected workflow activity job.");
            }

            return containsVideoStreamWithGivenAngleAndArn(videoStreamArn, viewAngle, activityJob);
        } catch (Exception ex) {
            log.error("Unable to get video stream data from DynamoDB.", ex);
            throw ex;
        }
    }

    private void UpdateVideoInfoInDynamoDBCompliant(final Path videoFilePath,
                                                    final String s3BucketName,
                                                    final String activityJobArn,
                                                    final ActivityType activityType) {
        String videoFileLocation = null;
        if ((videoFilePath != null) && (s3BucketName != null)) {
            String videoFileName = videoFilePath.toFile().getName();
            videoFileLocation = "s3://" + s3BucketName + "/" + S3_OBJECT_KEY_PREFIX + videoFileName;
        }
        ActivityJobItem activityJob = null;
        // Compliant: avoids using similar code fragments in the same file.
        activityJob = getJobFromArnAndActivityType(activityJobArn, activityType);
        if (activityJob == null) {
            return;
        }

        updateActivityJobItem(activityJob, videoFileLocation, activityType);
    }
    // {/fact}

    String S3_OBJECT_KEY_PREFIX="Object_Key_Prefix";

    private void updateActivityJobItem(ActivityJobItem activityJob, String videoFileLocation, ActivityType activityType) {
    }
    private boolean containsVideoStreamWithGivenAngleAndArn(final String videoStreamArn,
                                                            final String viewAngle,
                                                            final ActivityJobItem activityJob){
	return true;
    }
}

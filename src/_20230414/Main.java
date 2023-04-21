package _20230414;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        String s = "hello world";
        // 위에 주어진 문자열을 스트림 처리하여 println으로 출력하라.

        // 스트림처리를 해야하는데, 스트림처리라는 것은 끝이 없는 데이터에 흐름에 대해서 일정한 논리 단위를 정의하여 처리하는 것이다.
        // 자바에서 스트림을 다룰 때 가징 기본이 되는 것이 이진 데이터, 즉 바이트 데이터 단위로 처리하는 것이다.
        // 위에 데이터는 문자열 데이터인데, 이것을 이진데이터로 바로 볼 수 있는 방법은 자바에 없다.
        // 그래서 이진 데이터로 변환이 필요한데, 변환 하는 규칙은 인코딩에 의해 결정이 된다.
        // 그러면 스트림 처리를 위한 바이트 데이터를 생성해보자. 인코딩은 많이 사용하는 utf-8로 진행한다.
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        // 바이트 데이터가 주어졌고 이에 대해서 스트림 처리를 위해서 스트림 인스턴스를 생성한다.
        // 그런데 스트림의 구현체를 결정해야 한다. 구현체는 source의 위치에 따라 다르다.
        // 현재 읽을려고 하는 데이터 소스는 메모리에 위치해 있다.
        // 그렇기 때문에 ByteArray 구현체를 쓰면 된다.
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // 스트림 처리를 할 때 일정한 논리 단위로 처리를 한다.
        // 이러한 논리 단위를 처리하기 위해 물리적인 공간을 필요로 한다.
        // 그러한 물리적인 공간을 할당하자.
        byte[] buffer = new byte[1024];

        // 스트림 처리는 끝이 없는 데이터를 다루기 때문에, 반복문을 통해서 끝이 나올 때까지 처리해야 한다.
        while (true) {
            // 스트림의 끝은 데이터를 읽었을 때 구현체에 따라 다양하게 확인할 수 있다.
            // 먼저 끝 처리를 하기 전에 데이터를 읽어보자.
            int len = inputStream.read(buffer);
            // 여기서 len은 읽은 데이터의 길이이고 buffer에는 그 길이에 해당하는 데이터가 저장된다.
            // 저장된 데이터를 처리에 사용하면 된다.
            // 그런데 만약에 끝에 도달하는 경우에는 len의 값이 -1을 반환한다.
            // 스트림 끝에서는 더이상 처리할 것이 없기 때문에 stream 처리를 위해 열어두었던 반복문을 탈출한다.
            if (len == -1) {
                break;
            }

            // 여기서부터는 buffer에 데이터가 있기 때문에 논리 단위에 따라 처리를 하면된다.
            // 처리해야할 목적은 println으로 데이터를 출력하는 것이다.
            // 그런데 데이터는 이진데이터이고 println에서 요구하는 데이터는 stirng데이터이다.
            // 그렇기 때문에 이진데이터를 string 데이터로 변환해야 한다.
            // 이때도 변환 규칙이 필요한데, 이를 디코딩이라고 한다.
            // 디코딩하는 방식은 인코딩 했던 방식을 그대로 따라야 한다. utf-8로 했으니 utf-8로 다시 변환해야
            // 우리가 원하는 문자열을 얻을 수 있다.
            String string = new String(buffer, 0, len, StandardCharsets.UTF_8);

            // 문자열을 얻었으니 처리의 목표인 출력을 해보자
            System.out.println(string);
        }

    }
}

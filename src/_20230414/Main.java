package _20230414;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        String s = "hello world";
        // 위에 데이터가 주어졌을 때 inpustream을 활용하여 println으로 출력 해라!

        // inputstream은 스트림 처리를 하기 위한 interface이다.
        // interface는 기능의 역할을 묶어 둔 것이다.
        // 특정 기능 별로 구현체가 존재한다.
        // 어떤 구현체로 할 것인지는 기능을 사용하려는 대상에 따라 다르다.
        // 지금은 메모리에 있는 데이터를 읽을려고 하는 상황이다.
        // 컴퓨터에서 데이터는 기본적으로 이진 데이터로 읽는다.
        // 따라서 메모리에 있는 이진 데이터를 읽기 위해서는 byteArray 스트림 구현체가 필요한다.
        // 그런데 String은 아직 이진데이터를 바로 읽을 수 있도록 되어 있지가 않다.
        // String 객체에는 이진데이터에 바로 접글할 수 있는 메소드가 없기 때문에, 이진데이터로 변환이 필요하다.
        // 이진 데이터로 변환은 인코딩에 따라서 제공된다.
        // 자바의 스트링의 이진데이터는 유니코드로 되어있고 이 유니코드에 대한 이진코드 맵핑은 인코딩으로 결정된다.
        // 인코딩은 보편적으로 쓰이는 utf-8을 선택하여 스트링을 이진 데이터로 변환하겠다.
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        // 이진데이터가 나왔으니 사용하려고 했던 byteArray 구현체를 만들자/
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // 이제부터 구현체를 가지고 스트림처리를 할 것이다.
        // 스트림처리는 끝이 정해지지 않는 데이터에 대해서 일정한 논리 단위에 대해서 처리하는 것이다.
        // 기본적으로 처리를 요하는 논리 단위가 필요하다.
        // 여기서 처리할려고 하는 논리 단위는 utf-8로 인코딩된 문자열이다.
        // 이진 데이터를 끊어 읽는다면 논리 단위인 utf-9 인코딩이 깨질 수 있다.
        // 그래서 일정한 단위로 끊어서 읽을려고 한다.
        // 그 끊어 읽는 단위를 개항문자를 기준으로 해보자.
        // 개행 문자를 기준으로 했을 때 이진데이터를 다시 문자열 데이터로 변환하는 과정이 필요하다.
        // 이때 디코딩 설정을 해주어야 하는데 디코딩은 InputStreamReader에서 설정해줄 수 있다.
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        // 이제부터 스트림처리를 시작한다. 스트림 처리의 일정한 논리단위는 개행문자까지이다.
        // 이런 논리 단위를 담을 수 있는 물리적인 구현체(?) buffer을 만들어보자
        // reader를 사용하고 있고 개행문자를 검사할 수 있도록 char 버퍼가 필요하다.
        char[] buffer = new char[1024];

        // 이제 진짜로 스트림 처리를 시작한다. 계속해서 데이터가 끝없이 들어오기 때문에 반복문을 요구한다.
        // 반복문이 끝나는 조건은 reader에 더이상 읽을 것이 없을 때이다. 이를 for문으로 표현하기 보다 while문 내에 if문으로 표현하는게 낫다.
        while (true) {
            int len = reader.read(buffer);
            if (len == -1) {
                break;
            }

            // 처리할 논리 단위를 검사하는 부분이다.
            if (hasNewLine(buffer, len)) {
                System.out.println(new String(buffer, 0, len));
                continue;
            }

            // 만약에 처리할 논리단위가 없다면 어떻게 해야하나?
            // builder에 모아두고 일단 여기까지 작성!!
            // 더 생각하면 일단 프로그램이 복잡해진다. 여기서 피드백 받고 진행하기!!
        }
    }

    private static boolean hasNewLine(char[] buffer, int len) {
        for (int i = 0; i < len; i++) {
            if (buffer[i] == '\n') {
                return true;
            }
        }

        return false;
    }
}
